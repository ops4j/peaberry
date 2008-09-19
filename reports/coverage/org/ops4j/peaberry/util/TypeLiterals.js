var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":0,"sl":30,"methods":[{"sl":33,"el":33,"sc":3},{"sl":41,"el":44,"sc":3},{"sl":52,"el":55,"sc":3}],"el":56,"name":"TypeLiterals"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_0":{"methods":[{"sl":41}],"name":"configure","statements":[{"sl":43}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [0], [], [0], [], [], [], [], [], [], [], [], [], [], [], [], []]
