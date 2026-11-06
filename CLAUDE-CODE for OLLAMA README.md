# CLAUDE-CODE for OLLAMA
I installed Claude Code natively first
- I want to call Claude Code on another computer (Windwos 11 Ollama install:)

Installed via Homebrew:
`brew install --cask claude-code`

You can install it via node as well: `npm install -g @anthropic-ai/claude-code`

I am on MacOS and took the easy(?) path

Ollama lets you launch Claude against a local Ollama instace, but I want to call a network instance.  
- Web searching revealed setting the environment variable `ANTHROPIC_BASE_URL` to the URL of the Ollama instance.
- Created alias "claude-ollama" and I'm running great
- The Model MUST be hard coded.  Any changes you will need to change in .zshrc and restart
- You can specify different models for each ANTHROPIC OPUS, SONNET and HAIKU model.

```
  export OLLAMA_CLAUDE_MODEL="qwen3.8:27b"
  alias claude-ollama='ANTHROPIC_BASE_URL=http://luggable-home:11434 \
  ANTHROPIC_AUTH_TOKEN=ollama \
  ANTHROPIC_API_KEY="" \
  ANTHROPIC_MODEL=${OLLAMA_CLAUDE_MODEL} \
  ANTHROPIC_DEFAULT_OPUS_MODEL=${OLLAMA_CLAUDE_MODEL} \
  ANTHROPIC_DEFAULT_SONNET_MODEL=${OLLAMA_CLAUDE_MODEL} \
  ANTHROPIC_DEFAULT_HAIKU_MODEL=${OLLAMA_CLAUDE_MODEL} \
  claude \
  CLAUDE_CODE_MAX_CONTEXT_TOKENS=256000'
```

TEST: 
Ran a Claude Code session asking:
> "what is the most recent version of Spring Boot?  And which Java Version does it work with?   "?
- It got stuck them came back and reported: "I couldn't reach the web ..."



### Brave Search:
> Ollama on windows how do I enable it to do a web search?
> Claude Code on my MacOS iTerm calling Ollama on Windows 11 computer

> Add: OLLAMA_API_KEY = your-api-key-here
- Done

Run this command on the CLI to verify Ollama can reach the internet search:
```
$env:OLLAMA_API_KEY = "your-key-here"
curl https://ollama.com/api/web_search -H "Authorization: Bearer $env:OLLAMA_API_KEY" -H "Content-Type: application/json" -d '{"query":"test","max_results":1}'   
``` 
- Works on Mac
  - My M2 Nacbook Pro & M1 Max
  - Doesn't work on Windows 11

```
> $env:OLLAMA_API_KEY = "a630ccfeb4524489aa2dd1005af6907d.dW1IrL7omlSIDIXOaEXxe73b"
> curl.exe https://ollama.com/api/web_search -H "Authorization: Bearer $env:OLLAMA_API_KEY" -H "Content-Type: application/json" -d '{"query":"test","max_results":1}'
{"error": "bad json: invalid character 'q' looking for beginning of object key string"}
``` 

The Web Search doesn't happen because it needs an MPC server configured to the LLM for Web Search
- The Ollama LLMs dont have the tools inherent in them to web search

I added the MCP Server entry for Brave Search
- Created API tokens for both Tavily and Brave
- Added to ~/.claude.json and web searches

Another problem presented itself:
- Claude was waiting for teh LLM to response: Qwen3.8:27b-mlx on teh M1 Max.
- Retrying on  Windows 11 128GB Luggable -- expecting this to succeed

Reduced tokens context to 64000 - should speed response.
Gemini recommended adding this to the Brave Search MCP Server:

```
{
  "env": {
    "API_TIMEOUT_MS": "1200000",
    "CLAUDE_STREAM_IDLE_TIMEOUT_MS": "1200000"
  }
}
```

