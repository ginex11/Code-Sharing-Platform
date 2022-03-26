<!DOCTYPE html>
<html>
<head>
    <title>Code</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<span id="load_date"> <pre><code>${snippet.date}</code></pre></span>
<#if snippet.restrictedByView><span id="views_restriction">${snippet.views} more views allowed</span></#if>
<#if snippet.restrictedByTime><span id="time_restriction">The code will be available for ${snippet.time} seconds</span></#if>
<pre id="code_snippet"><pre><code>${snippet.code}</code></pre></pre>
</body>
</html>