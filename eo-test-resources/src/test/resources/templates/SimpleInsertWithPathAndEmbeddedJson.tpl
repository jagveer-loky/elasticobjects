<call values="map">{"test":"testValue","path":{"test2":"testValue2"}</call>
Test $[test] <call loopPath="path">Insert: $[test2] </call>$[path/test2]