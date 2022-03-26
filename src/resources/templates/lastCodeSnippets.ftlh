<!DOCTYPE html>
<html>
<head>
    <title>Latest</title>
    <link rel="stylesheet" href="../css/style.css"/>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>

<table>
    <#list allCodeSnippets as snippet>
        <tr>
            <th><span id="load_date"><code>${snippet.date}</code></span></th>
        </tr>
        <tr>
            <td>
                <pre id="code_snippet"><code>${snippet.code}</code></pre>
            </td>
        </tr>

    </#list>
</table>
</body>
</html>