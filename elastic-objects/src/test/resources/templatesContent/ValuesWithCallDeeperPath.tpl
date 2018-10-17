The value can be set by path:
level0/level1/level2/level3/level4/string='$[level0/level1/level2/level3/level4/string]'
Now the call solution with subsequent path:
<call path="level0"><call><call path="level1"><call><call><call path="level2"><call path="level3"><call path="level4">This is the value with string='$[string]'</call></call></call></call></call></call></call></call>