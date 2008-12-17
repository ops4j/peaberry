var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1324,"sl":38,"methods":[{"sl":44,"el":47,"sc":3},{"sl":49,"el":59,"sc":3},{"sl":61,"el":66,"sc":3},{"sl":68,"el":70,"sc":3}],"el":71,"name":"IdServiceImpl"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_25":{"methods":[{"sl":49},{"sl":61},{"sl":68}],"name":"testServiceExports","statements":[{"sl":50},{"sl":51},{"sl":53},{"sl":54},{"sl":55},{"sl":57},{"sl":62},{"sl":63},{"sl":64},{"sl":69}],"pass":true},"test_32":{"methods":[{"sl":49},{"sl":61},{"sl":68}],"name":"testDecoratedServiceInjection","statements":[{"sl":50},{"sl":51},{"sl":53},{"sl":54},{"sl":55},{"sl":57},{"sl":62},{"sl":69}],"pass":true},"test_15":{"methods":[{"sl":61},{"sl":68}],"name":"testDirectServiceInjection","statements":[{"sl":62},{"sl":63},{"sl":64},{"sl":69}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [32, 25], [32, 25], [32, 25], [], [32, 25], [32, 25], [32, 25], [], [32, 25], [], [], [], [32, 15, 25], [32, 15, 25], [15, 25], [15, 25], [], [], [], [32, 15, 25], [32, 15, 25], [], []]
