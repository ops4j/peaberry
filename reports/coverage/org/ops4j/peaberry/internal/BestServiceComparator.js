var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":223,"sl":33,"methods":[{"sl":38,"el":57,"sc":3},{"sl":60,"el":66,"sc":3}],"el":67,"name":"BestServiceComparator"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_0":{"methods":[{"sl":38},{"sl":60}],"name":"brokenServices","statements":[{"sl":40},{"sl":41},{"sl":43},{"sl":47},{"sl":48},{"sl":50},{"sl":52},{"sl":61},{"sl":62},{"sl":63}],"pass":true},"test_9":{"methods":[{"sl":38},{"sl":60}],"name":"checkRanking","statements":[{"sl":40},{"sl":41},{"sl":43},{"sl":47},{"sl":48},{"sl":50},{"sl":52},{"sl":56},{"sl":61},{"sl":62},{"sl":63}],"pass":true},"test_5":{"methods":[{"sl":38},{"sl":60}],"name":"cornerCases","statements":[{"sl":40},{"sl":41},{"sl":43},{"sl":44},{"sl":47},{"sl":48},{"sl":50},{"sl":52},{"sl":61},{"sl":62},{"sl":63},{"sl":65}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [9, 5, 0], [], [9, 5, 0], [9, 5, 0], [], [9, 5, 0], [5], [], [], [9, 5, 0], [9, 5, 0], [], [9, 5, 0], [], [9, 5, 0], [], [], [], [9], [], [], [], [9, 5, 0], [9, 5, 0], [9, 5, 0], [9, 5, 0], [], [5], [], []]
