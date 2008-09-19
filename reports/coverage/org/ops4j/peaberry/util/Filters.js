var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":150,"sl":29,"methods":[{"sl":32,"el":32,"sc":3},{"sl":42,"el":44,"sc":3},{"sl":52,"el":65,"sc":3}],"el":66,"name":"Filters"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_4":{"methods":[{"sl":42}],"name":"testHashCodeAndEquals","statements":[{"sl":43}],"pass":true},"test_10":{"methods":[{"sl":42},{"sl":52}],"name":"testObjectClassConverter","statements":[{"sl":43},{"sl":53},{"sl":55},{"sl":56},{"sl":60},{"sl":61}],"pass":true},"test_8":{"methods":[{"sl":42}],"name":"testBogusFilterString","statements":[{"sl":43}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [10, 8, 4], [10, 8, 4], [], [], [], [], [], [], [], [], [10], [10], [], [10], [10], [], [], [], [10], [10], [], [], [], [], []]
