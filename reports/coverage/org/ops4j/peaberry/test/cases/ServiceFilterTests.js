var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1364,"sl":39,"methods":[{"sl":47,"el":52,"sc":3},{"sl":54,"el":59,"sc":3},{"sl":61,"el":70,"sc":3},{"sl":72,"el":87,"sc":3}],"el":88,"name":"ServiceFilterTests"},{"id":1364,"sl":41,"methods":[],"el":41,"name":"ServiceFilterTests.A"},{"id":1364,"sl":43,"methods":[],"el":43,"name":"ServiceFilterTests.B"},{"id":1364,"sl":45,"methods":[],"el":45,"name":"ServiceFilterTests.C"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_4":{"methods":[{"sl":47}],"name":"testSingleObjectClassFilter","statements":[{"sl":48},{"sl":49},{"sl":51}],"pass":true},"test_26":{"methods":[{"sl":61}],"name":"testFilterHashCodeAndEquals","statements":[{"sl":62},{"sl":63},{"sl":65},{"sl":66},{"sl":67},{"sl":69}],"pass":true},"test_23":{"methods":[{"sl":54}],"name":"testMultipleObjectClassFilter","statements":[{"sl":55},{"sl":56},{"sl":58}],"pass":true},"test_8":{"methods":[{"sl":72}],"name":"testBrokenLdapFilterStrings","statements":[{"sl":73},{"sl":74},{"sl":78},{"sl":79},{"sl":83},{"sl":84}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [4], [4], [4], [], [4], [], [], [23], [23], [23], [], [23], [], [], [26], [26], [26], [], [26], [26], [26], [], [26], [], [], [8], [8], [8], [], [], [], [8], [8], [], [], [], [8], [8], [], [], [], []]
