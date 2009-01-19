var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":750,"sl":30,"methods":[{"sl":33,"el":33,"sc":3},{"sl":41,"el":44,"sc":3},{"sl":52,"el":55,"sc":3}],"el":56,"name":"TypeLiterals"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_3":{"methods":[{"sl":41}],"name":"configure","statements":[{"sl":43}],"pass":true},"test_11":{"methods":[{"sl":52}],"name":"testServiceLookupPerformance","statements":[{"sl":54}],"pass":true},"test_25":{"methods":[{"sl":41},{"sl":52}],"name":"configure","statements":[{"sl":43},{"sl":54}],"pass":true},"test_24":{"methods":[{"sl":41},{"sl":52}],"name":"configure","statements":[{"sl":43},{"sl":54}],"pass":true},"test_21":{"methods":[{"sl":52}],"name":"configure","statements":[{"sl":54}],"pass":true},"test_5":{"methods":[{"sl":52}],"name":"testDirectServiceInjection","statements":[{"sl":54}],"pass":true},"test_18":{"methods":[{"sl":41}],"name":"configure","statements":[{"sl":43}],"pass":true},"test_8":{"methods":[{"sl":52}],"name":"configure","statements":[{"sl":54}],"pass":true},"test_19":{"methods":[{"sl":52}],"name":"configure","statements":[{"sl":54}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [18, 24, 3, 25], [], [18, 24, 3, 25], [], [], [], [], [], [], [], [], [5, 19, 24, 11, 8, 21, 25], [], [5, 19, 24, 11, 8, 21, 25], [], []]
