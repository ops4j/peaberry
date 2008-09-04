var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":923,"sl":33,"methods":[{"sl":35,"el":49,"sc":3},{"sl":51,"el":56,"sc":3}],"el":57,"name":"AttributeTests"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":35}],"name":"testPropertyConverter","statements":[{"sl":36},{"sl":38},{"sl":39},{"sl":40},{"sl":42},{"sl":44},{"sl":45},{"sl":46},{"sl":48}],"pass":true},"test_2":{"methods":[{"sl":51}],"name":"testNameConverter","statements":[{"sl":52},{"sl":53}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [1], [1], [], [1], [1], [1], [], [1], [], [1], [1], [1], [], [1], [], [], [2], [2], [2], [], [], [], []]
