var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":0,"sl":30,"methods":[{"sl":33,"el":33,"sc":3},{"sl":41,"el":44,"sc":3},{"sl":52,"el":55,"sc":3}],"el":56,"name":"TypeLiterals"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_3":{"methods":[{"sl":52}],"name":"configure","statements":[{"sl":54}],"pass":true},"test_4":{"methods":[{"sl":52}],"name":"configure","statements":[{"sl":54}],"pass":true},"test_26":{"methods":[{"sl":41},{"sl":52}],"name":"configure","statements":[{"sl":43},{"sl":54}],"pass":true},"test_10":{"methods":[{"sl":41}],"name":"configure","statements":[{"sl":43}],"pass":true},"test_0":{"methods":[{"sl":52}],"name":"configure","statements":[{"sl":54}],"pass":true},"test_21":{"methods":[{"sl":52}],"name":"testServiceExports","statements":[{"sl":54}],"pass":true},"test_14":{"methods":[{"sl":52}],"name":"testDirectServiceInjection","statements":[{"sl":54}],"pass":true},"test_15":{"methods":[{"sl":41}],"name":"configure","statements":[{"sl":43}],"pass":true},"test_7":{"methods":[{"sl":41},{"sl":52}],"name":"configure","statements":[{"sl":43},{"sl":54}],"pass":true},"test_19":{"methods":[{"sl":52}],"name":"testServiceLookupPerformance","statements":[{"sl":54}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [26, 10, 7, 15], [], [26, 10, 7, 15], [], [], [], [], [], [], [], [], [26, 14, 19, 0, 21, 3, 7, 4], [], [26, 14, 19, 0, 21, 3, 7, 4], [], []]
