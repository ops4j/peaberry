var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1812,"sl":41,"methods":[{"sl":47,"el":51,"sc":3},{"sl":53,"el":59,"sc":3},{"sl":61,"el":69,"sc":3},{"sl":71,"el":76,"sc":3},{"sl":78,"el":82,"sc":3},{"sl":84,"el":91,"sc":3}],"el":92,"name":"IdManagerImpl"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_28":{"methods":[{"sl":53},{"sl":61},{"sl":71},{"sl":78},{"sl":84}],"name":"testServiceInjection","statements":[{"sl":54},{"sl":55},{"sl":56},{"sl":57},{"sl":62},{"sl":63},{"sl":64},{"sl":65},{"sl":66},{"sl":67},{"sl":72},{"sl":75},{"sl":79},{"sl":81},{"sl":85},{"sl":86},{"sl":87},{"sl":90}],"pass":true},"test_9":{"methods":[{"sl":53},{"sl":71},{"sl":78},{"sl":84}],"name":"testDecoratedServiceInjection","statements":[{"sl":54},{"sl":55},{"sl":56},{"sl":57},{"sl":72},{"sl":75},{"sl":79},{"sl":81},{"sl":85},{"sl":86},{"sl":87},{"sl":90}],"pass":true},"test_5":{"methods":[{"sl":71}],"name":"testDirectServiceInjection","statements":[{"sl":72},{"sl":73},{"sl":75}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [28, 9], [28, 9], [28, 9], [28, 9], [28, 9], [], [], [], [28], [28], [28], [28], [28], [28], [28], [], [], [], [5, 28, 9], [5, 28, 9], [5], [], [5, 28, 9], [], [], [28, 9], [28, 9], [], [28, 9], [], [], [28, 9], [28, 9], [28, 9], [28, 9], [], [], [28, 9], [], []]
