param(
    [Parameter(Mandatory = $true)]
    [string]$ApiKey,

    [Parameter(Mandatory = $true)]
    [string]$ElasticsearchUris,

    [string]$BaseUrl = "https://dashscope.aliyuncs.com/compatible-mode",
    [string]$EmbeddingModel = "text-embedding-v4",
    [string]$IndexName = "aimall-rag-eval-vectorstore",
    [int]$Dimensions = 1024,
    [string]$TopKList = "5,10,30",
    [int]$ProductCount = 10000,
    [ValidateRange(1, 10)]
    [int]$EmbeddingBatchSize = 10,
    [bool]$ReseedProducts = $true,
    [double]$MinRecallAt10 = 0.80,
    [double]$MinRecallAt30 = 0.90,
    [string]$Maven = "C:\Users\12761\tools\apache-maven-3.9.16\bin\mvn.cmd"
)

$ErrorActionPreference = "Stop"

if ([string]::IsNullOrWhiteSpace($ApiKey) -or $ApiKey.Contains("111111111111")) {
    throw "Missing real spring.ai.openai.api-key. RAG recall evaluation will not run."
}

if ([string]::IsNullOrWhiteSpace($ElasticsearchUris)) {
    throw "Missing spring.elasticsearch.uris, for example: http://localhost:9200"
}

$normalizedBaseUrl = $BaseUrl.TrimEnd("/")
if ($normalizedBaseUrl.EndsWith("/v1")) {
    $normalizedBaseUrl = $normalizedBaseUrl.Substring(0, $normalizedBaseUrl.Length - 3)
}

if (-not (Test-Path $Maven)) {
    $mvnFromPath = Get-Command mvn.cmd -ErrorAction SilentlyContinue
    if ($null -eq $mvnFromPath) {
        throw "Maven was not found. Install Maven or pass -Maven with the mvn.cmd path."
    }
    $Maven = $mvnFromPath.Source
}

& $Maven `
    -pl aimall-web `
    -am `
    "-Dtest=com.aimall.evaluation.RagRecallEvaluationTest" `
    "-Dsurefire.failIfNoSpecifiedTests=false" `
    "-Drag.eval.enabled=true" `
    "-Dspring.ai.openai.api-key=$ApiKey" `
    "-Dspring.ai.openai.base-url=$normalizedBaseUrl" `
    "-Dspring.ai.openai.embedding.options.model=$EmbeddingModel" `
    "-Dspring.elasticsearch.uris=$ElasticsearchUris" `
    "-Drag.eval.index-name=$IndexName" `
    "-Drag.eval.dimensions=$Dimensions" `
    "-Drag.eval.top-k-list=$TopKList" `
    "-Drag.eval.product-count=$ProductCount" `
    "-Drag.eval.embedding-batch-size=$EmbeddingBatchSize" `
    "-Drag.eval.reseed-products=$ReseedProducts" `
    "-Drag.eval.min-recall-at-10=$MinRecallAt10" `
    "-Drag.eval.min-recall-at-30=$MinRecallAt30" `
    test

exit $LASTEXITCODE
