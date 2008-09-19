var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":288,"sl":29,"methods":[{"sl":36,"el":42,"sc":3},{"sl":44,"el":55,"sc":3},{"sl":57,"el":61,"sc":3}],"el":62,"name":"OSGiServiceImport"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":36},{"sl":44},{"sl":57}],"name":"testServiceInjection","statements":[{"sl":39},{"sl":40},{"sl":41},{"sl":45},{"sl":46},{"sl":47},{"sl":51},{"sl":52},{"sl":54},{"sl":58},{"sl":59}],"pass":true},"test_3":{"methods":[{"sl":36},{"sl":44},{"sl":57}],"name":"testServiceTypes","statements":[{"sl":39},{"sl":40},{"sl":41},{"sl":45},{"sl":46},{"sl":47},{"sl":51},{"sl":54},{"sl":58},{"sl":59}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [3, 1], [], [], [3, 1], [3, 1], [3, 1], [], [], [3, 1], [3, 1], [3, 1], [3, 1], [], [], [], [3, 1], [1], [], [3, 1], [], [], [3, 1], [3, 1], [3, 1], [], [], []]
