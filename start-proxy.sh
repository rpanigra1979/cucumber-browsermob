echo "Killing any pre-existing browsermob proxy app"
kill `ps -ef | grep 5555 | awk '{ print $2 }'`
echo "Starting browsermobproxy app at port 5555"
sh /Users/rpanigr/presentation/browsermob-proxy-2.0-beta-9/bin/browsermob-proxy -port 5555 &
sleep 5
echo "Starting proxy at 5556"
curl -X POST -d 'port=5556' http://localhost:5555/proxy
echo "Creating HAR (HTTP Archive)"
curl -X PUT http://localhost:5555/proxy/5556/har
