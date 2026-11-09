# Ollama Server-side configuration (M1 Max & Windows) 

Good news — **64K is actually fine for your setup.** I was too conservative earlier. Here's why:

**Qwen3.8 27B is a hybrid architecture** — only 16 of its 64 layers use full attention (the rest are linear attention). That means the KV cache is tiny compared to a standard 64-layer model:

| Context | KV cache (f16) | KV cache (q8_0) | Total RAM | Fits 32GB? |
|---------|---------------|-----------------|-----------|------------|
| 32K | ~2 GB | ~1 GB | ~21–22 GB | ✅ Easy |
| **64K** | **~4 GB** | **~2 GB** | **~24–27 GB** | **✅ Recommended** |
| 128K | ~8 GB | ~4 GB | ~28–32 GB | ⚠️ Tight |

The model card for `qwen3.8:27b` on 32GB explicitly lists 64K as the recommended context. So keep it at 64K.

**Your real problem is likely missing these server-side env vars:**

```bash
export OLLAMA_FLASH_ATTENTION=1
export OLLAMA_KV_CACHE_TYPE=q8_0
export OLLAMA_KEEP_ALIVE=30m
```

Without `q8_0`, your 64K KV cache is 2× bigger (4 GB instead of 2 GB), and without flash attention the memory math gets worse. That's probably what's pushing you into swap and causing the timeout.

**Why 64K and not 32K?** Claude Code's system prompt + tool definitions alone eat ~20–30K tokens. A typical "create an app" session will hit 40–60K tokens within a few turns. At 32K, Claude Code would trigger its compaction threshold (~83.5% usage) almost immediately, degrading quality and causing repeated re-summarization. 64K gives you a full working session.

**TL;DR:** Keep `OLLAMA_CONTEXT_LENGTH=64000`. Add `q8_0` + flash attention on the M1 Max server. That's the fix, not a lower context number.


If you're using the Ollama menu-bar app:
```
launchctl setenv OLLAMA_FLASH_ATTENTION 1
launchctl setenv OLLAMA_KV_CACHE_TYPE q8_0
```

Then quit and reopen the app.

If you're running ollama serve manually in a terminal:
```
export OLLAMA_FLASH_ATTENTION=1
export OLLAMA_KV_CACHE_TYPE=q8_0
ollama serve
```

**No — moving the Claude Code client to the M4 Max won't help.** The client just sends/receives HTTP. All the heavy lifting (prefill + token generation) happens on the M1 Max server. A faster client doesn't make a slower server faster.

**The M1 Max 32GB is your bottleneck**, but it's a fixable one. At 400 GB/s, the M1 Max should hit ~19 tok/s on a 27B Q4 model. The timeouts are almost certainly from **context length pressure**, not raw speed.

Here's the math on your 32GB M1 Max:

| Component | Size |
|---|---|
| Model weights (Q4) | ~17–18 GB |
| OS + overhead | ~4–5 GB |
| KV cache @ 32K (f16) | ~2 GB |
| KV cache @ 64K (f16) | ~4 GB |
| **Total @ 32K** | **~23–25 GB** (tight) |
| **Total @ 64K** | **~25–27 GB** (very tight) |

Claude Code's "application creation" prompts (system prompt + tool defs + multi-file context) can easily push past 32K tokens. When that happens, Ollama either truncates, stalls, or the request exceeds the allocated context and times out.

**Fix on the M1 Max server:**

```bash
# Enable flash attention + q8_0 KV cache (halves KV memory)
export OLLAMA_FLASH_ATTENTION=1
export OLLAMA_KV_CACHE_TYPE=q8_0
export OLLAMA_CONTEXT_LENGTH=32768

# Keep model loaded between Claude Code requests
export OLLAMA_KEEP_ALIVE=30m

ollama serve
```

With `q8_0` KV cache, 32K context drops from ~2 GB to ~1 GB, giving you breathing room. 64K becomes viable (~2 GB KV instead of ~4 GB).

**Why Ollama cloud works:** Their servers have 48–80+ GB of VRAM with the full context window open. No memory pressure, no timeout.

**Bottom line:** Your M1 Max 32GB can run this model, but only with flash attention + q8_0 cache + capped context. The M4 Max client is irrelevant to the timeout. If you want headroom without squeezing settings, you'd need a 64GB M1 Max or M2/M4 Max server.



