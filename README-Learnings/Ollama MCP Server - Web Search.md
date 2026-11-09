
> macOS running Claude Code using Ollama LLM on another computer (local network) doesnt reach out to the internet to do a web search.
>
> This is the alias I defined in my .zshrc
>
`export OLLAMA_CLAUDE_MODEL="qwen3.8:27b-mlx"
alias claude-ollama='ANTHROPIC_BASE_URL=http://mac-studio:11434 \
ANTHROPIC_AUTH_TOKEN=ollama \
ANTHROPIC_API_KEY="" \
ANTHROPIC_MODEL=${OLLAMA_CLAUDE_MODEL} \
ANTHROPIC_DEFAULT_OPUS_MODEL=${OLLAMA_CLAUDE_MODEL} \
ANTHROPIC_DEFAULT_SONNET_MODEL=${OLLAMA_CLAUDE_MODEL} \
ANTHROPIC_DEFAULT_HAIKU_MODEL=${OLLAMA_CLAUDE_MODEL} \
claude \
CLAUDE_CODE_MAX_CONTEXT_TOKENS=256000'`

> How do I configure Claude Code to use Ollama LLM on another local computer, but still enable it to search the internet.


When you route Claude Code through an OpenAI/Anthropic-compatible proxy like Ollama (`ANTHROPIC_BASE_URL=http://mac-studio:11434`), Claude Code sends all its internal requests directly to that local endpoint. Because Ollama is purely a local inference engine and doesn't natively host or support Anthropic's server-side internet retrieval tools, any web search request fails.

To fix this, you need to handle web search **client-side on your macOS machine** using a Model Context Protocol (MCP) search server (such as Tavily, Brave Search, or DuckDuckGo MCP), which allows Claude Code to fetch search results locally and pass them into your Qwen model.


---

### Step 1: Install a Web Search MCP Server on macOS

Claude Code natively supports MCP servers. You can set up a search provider (like Brave Search or Tavily) locally on your Mac so Claude Code can perform web lookups independently of Ollama.

1. Obtain a free API key from a search provider supported by MCP (e.g., **Brave Search** or **Tavily**).
2. Configure your MCP server in your Claude configuration file on macOS (`~/.claude.json` or via your Claude CLI settings). For example, using the Brave Search MCP server:

```json
{
  "mcpServers": {
    "brave-search": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-brave-search"],
      "env": {
        "BRAVE_API_KEY": "your_brave_search_api_key"
      }
    }
  }
}

```

---

### Step 2: Verify Your Local MCP Integration

Once the MCP server is configured in your global or project-level Claude configuration:

1. Restart your terminal session or reload your `.zshrc` (`source ~/.zshrc`).
2. Run your `claude-ollama` alias.
3. Test by asking Claude Code to look up a live piece of information or search the web. Claude will execute the tool locally through the MCP server and feed the text context back into your Qwen model running on the other computer.


I added this to ~/.claude.json and web seearches started succeeding


Another problem presented itself:
- Claude was waiting for teh LLM to response: Qwen3.8:27b-mlx on teh M1 Max.
- Retrying on  Windows 11 128GB Luggable -- expecting this to succeed (Failed)
- 
Retrying the query against the Windows 11 128GB Luggable -- expecting this to succeed
---

## Change Token Size and add Timeout
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