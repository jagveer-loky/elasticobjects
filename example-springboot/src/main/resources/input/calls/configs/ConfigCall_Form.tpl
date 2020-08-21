$[(ConfigCall)configuration configType="eo->configType." configFilter="eo->configFilter."/]
<html>
    <head>
    <title>Configurations for class '$[configType]' and key</title>
    </head>
    <body>

     <a href="/config">Cached Configurations</a> - <a href="/config/$[configType]">$[configFilter]</a>"

     <h1>Cached configuration for '$[configType]'  and key '$[configFilter]' for scope ='" + scope + "'</h1>
        <p>You're logged in with the roles $role + $permission
        </p>
        <xmp style="background-color:lightgray;border:1px solid darkgray;">$[configuration/0]</xmp>

        Function $[configFilter]
        <form action="/eo" method="POST">
        <textarea name="eo" cols="60" rows="6" style="background-color:red">
            ($[configuration/0/modelKeys])
        </textarea>
        <input type="submit" value="post"/>
       </form>
    </body>
</html>