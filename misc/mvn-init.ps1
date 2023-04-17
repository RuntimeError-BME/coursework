# Set the URL and file name of the Apache Maven binary distribution archive
$url = "https://www.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz"
$fileName = "apache-maven-3.6.3-bin.tar.gz"

# Set the directory to extract the archive to
$extractDir = "./dist/maven"

# Download the archive
Invoke-WebRequest -Uri $url -OutFile $fileName

# Extract the archive
New-Item -ItemType Directory -Force -Path $extractDir
tar -xzf $fileName -C $extractDir --strip-components=1

# Add the Apache Maven "bin" directory to the PATH environment variable
$env:Path += ";$extractDir\bin"

# Test that Maven is installed and working
mvn --version