<call values="map">
[
    {
        "header":"header1",
        "summary":"summary1",
        "content":"content1",
        "additional":"additional1",
        "template":"H",
        "headerLevel":1
    },
    {
        "header":"header2",
        "summary":"summary2",
        "content":"content2",
        "additional":"additional2",
        "template":"H",
        "headerLevel":2
    }
]
</call>
<call templateKey="$[template].md" path="*" />