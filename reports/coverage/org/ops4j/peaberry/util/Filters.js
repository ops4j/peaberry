var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":169,"sl":29,"methods":[{"sl":32,"el":32,"sc":3},{"sl":42,"el":44,"sc":3},{"sl":52,"el":65,"sc":3}],"el":66,"name":"Filters"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_11":{"methods":[{"sl":42}],"name":"testBogusFilterString","statements":[{"sl":43}],"pass":true},"test_10":{"methods":[{"sl":42}],"name":"testHashCodeAndEquals","statements":[{"sl":43}],"pass":true},"test_18":{"methods":[{"sl":42},{"sl":52}],"name":"testObjectClassConverter","statements":[{"sl":43},{"sl":53},{"sl":55},{"sl":56},{"sl":60},{"sl":61}],"pass":true},"test_8":{"methods":[{"sl":42}],"name":"testCaseInsensitivity","statements":[{"sl":43}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [11, 8, 18, 10], [11, 8, 18, 10], [], [], [], [], [], [], [], [], [18], [18], [], [18], [18], [], [], [], [18], [18], [], [], [], [], []]
