filePath=`echo $1 | sed 's/example-springboot\///'`
echo transfering example-springboot/$filePath root@87.106.171.45:$filePath
scp -r example-springboot/$filePath  root@87.106.171.45:$filePath
