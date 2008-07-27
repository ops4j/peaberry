var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"sl":33,"el":67,"methods":[{"sl":38,"el":57,"sc":3},{"sl":60,"el":66,"sc":3}],"name":"BestServiceComparator","id":41}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_4":{"statements":[{"sl":40},{"sl":41},{"sl":43},{"sl":44},{"sl":47},{"sl":48},{"sl":50},{"sl":52},{"sl":61},{"sl":62},{"sl":63},{"sl":65}],"pass":true,"methods":[{"sl":38},{"sl":60}],"name":"cornerCases"},"test_17":{"statements":[{"sl":40},{"sl":41},{"sl":43},{"sl":47},{"sl":48},{"sl":50},{"sl":52},{"sl":56},{"sl":61},{"sl":62},{"sl":63}],"pass":true,"methods":[{"sl":38},{"sl":60}],"name":"checkRanking"},"test_3":{"statements":[{"sl":40},{"sl":41},{"sl":43},{"sl":47},{"sl":48},{"sl":50},{"sl":52},{"sl":61},{"sl":62},{"sl":63}],"pass":true,"methods":[{"sl":38},{"sl":60}],"name":"brokenServices"}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [17, 3, 4], [], [17, 3, 4], [17, 3, 4], [], [17, 3, 4], [4], [], [], [17, 3, 4], [17, 3, 4], [], [17, 3, 4], [], [17, 3, 4], [], [], [], [17], [], [], [], [17, 3, 4], [17, 3, 4], [17, 3, 4], [17, 3, 4], [], [4], [], []]
