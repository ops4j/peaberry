var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":148,"sl":29,"methods":[{"sl":32,"el":32,"sc":3},{"sl":42,"el":44,"sc":3},{"sl":52,"el":65,"sc":3}],"el":66,"name":"Filters"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_2":{"methods":[{"sl":42}],"name":"configure","statements":[{"sl":43}],"pass":true},"test_4":{"methods":[{"sl":42},{"sl":52}],"name":"testSingleObjectClassFilter","statements":[{"sl":43},{"sl":53},{"sl":55},{"sl":56},{"sl":60},{"sl":64}],"pass":true},"test_12":{"methods":[{"sl":42}],"name":"testBrokenLdapFilterStrings","statements":[{"sl":43}],"pass":true},"test_14":{"methods":[{"sl":42},{"sl":52}],"name":"testMultipleObjectClassFilter","statements":[{"sl":43},{"sl":53},{"sl":55},{"sl":56},{"sl":60},{"sl":61}],"pass":true},"test_7":{"methods":[{"sl":42}],"name":"testFilterHashCodeAndEquals","statements":[{"sl":43}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [4, 12, 14, 7, 2], [4, 12, 14, 7, 2], [], [], [], [], [], [], [], [], [4, 14], [4, 14], [], [4, 14], [4, 14], [], [], [], [4, 14], [14], [], [], [4], [], []]
