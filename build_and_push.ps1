$ErrorActionPreference = "Stop"

$DOCKER_HUB_USER = "jjtrujillo"
$VERSION = "latest"

Write-Host "Construyendo y subiendo imágenes a Docker Hub ($DOCKER_HUB_USER)..." -ForegroundColor Cyan

# Lista de servicios basada en tu docker-compose.yml
$services = @(
    @{ Name = "python-service"; Path = "./python-service" },
    @{ Name = "java-service"; Path = "./java-service" },
    @{ Name = "frontend"; Path = "./frontend" }
)

foreach ($service in $services) {
    $imageName = "$($DOCKER_HUB_USER)/$($service.Name):$VERSION"
    
    Write-Host "`n---> Procesando $($service.Name)" -ForegroundColor Yellow
    
    Write-Host "1. Construyendo la imagen..."
    docker build -t $imageName $service.Path
    
    Write-Host "2. Subiendo la imagen a Docker Hub..."
    docker push $imageName
    
    Write-Host "Hecho: $imageName" -ForegroundColor Green
}

Write-Host "`n¡Proceso completado exitosamente!" -ForegroundColor Cyan
