* <a href="#SYNOPSIS">SYNOPSIS</a>
* <a href="#SYNOPSIS">DESCRIPTION</a>
* <a href="#OPTIONS">OPTIONS</a>
  * <call path="OPTIONS/*"><call if="'key' ne 'entry'"><a href="#$[key]">$[key]</a>, </call></call>
<call if="'$[EXAMPLES/entry]'">* <a href="#EXAMPLES">EXAMPLES</a></call>
<call if="'$[DISCUSSION/entry]'">* <a href="#DISCUSSION">DISCUSSION</a></call>

<call path="NAME/entry">
$[description]
</call>

<call path="SYNOPSIS/entry">
<a name="$[part]"></a>
## $[part]
```$[description]
```
</call>

<call path="DESCRIPTION/entry">
<a name="$[part]"></a>
## $[part]
$[description]
</call>

<call path="OPTIONS/entry">
<a name="$[part]"></a>
## $[part]
$[description]
</call>
<call path="OPTIONS/*">
<call if="'key' ne 'entry'">
<a name="$[key]"></a>
### --$[key] <call if="'$[short|]'">, -$[short]</call> <call if="'$[args|]'"> $[args]</call>
$[description]
</call>
</call>

<call path="EXAMPLES/entry" if="'$[EXAMPLES/entry]'">
<a name="$[part]"></a>
## $[part]
$[description|]
</call>

<call path="DISCUSSION/entry"  if="'$[DISCUSSION/entry]'">
<a name="$[part]"></a>
## $[part]
$[description|]
</call>



