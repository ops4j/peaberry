var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":25,"sl":30,"methods":[{"sl":35,"el":37,"sc":3},{"sl":39,"el":42,"sc":3},{"sl":44,"el":47,"sc":3},{"sl":49,"el":52,"sc":3},{"sl":54,"el":57,"sc":3},{"sl":59,"el":63,"sc":3},{"sl":65,"el":69,"sc":3},{"sl":71,"el":75,"sc":3}],"el":76,"name":"AttributeDictionary"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_5":{"methods":[{"sl":35},{"sl":49},{"sl":54},{"sl":59},{"sl":65},{"sl":71}],"name":"testAttributeDictionaryAdapter","statements":[{"sl":36},{"sl":51},{"sl":56},{"sl":62},{"sl":68},{"sl":74}],"pass":true},"test_6":{"methods":[{"sl":35},{"sl":39},{"sl":44}],"name":"testServiceExports","statements":[{"sl":36},{"sl":41},{"sl":46}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [5, 6], [5, 6], [], [], [6], [], [6], [], [], [6], [], [6], [], [], [5], [], [5], [], [], [5], [], [5], [], [], [5], [], [], [5], [], [], [5], [], [], [5], [], [], [5], [], [], [5], [], []]
