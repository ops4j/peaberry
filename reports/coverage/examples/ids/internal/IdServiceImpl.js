var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":696,"sl":26,"methods":[{"sl":31,"el":33,"sc":3},{"sl":35,"el":38,"sc":3},{"sl":40,"el":46,"sc":3}],"el":47,"name":"IdServiceImpl"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":31},{"sl":35},{"sl":40}],"name":"testServiceInjection","statements":[{"sl":32},{"sl":37},{"sl":42},{"sl":43}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [1], [1], [], [], [1], [], [1], [], [], [1], [], [1], [1], [], [], [], []]
