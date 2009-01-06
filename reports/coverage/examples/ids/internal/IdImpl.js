var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1844,"sl":26,"methods":[{"sl":31,"el":33,"sc":3},{"sl":35,"el":38,"sc":3},{"sl":40,"el":43,"sc":3},{"sl":45,"el":51,"sc":3}],"el":52,"name":"IdImpl"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":31},{"sl":35}],"name":"testServiceExports","statements":[{"sl":32},{"sl":37}],"pass":true},"test_12":{"methods":[{"sl":35}],"name":"testDirectServiceInjection","statements":[{"sl":37}],"pass":true},"test_21":{"methods":[{"sl":31},{"sl":35},{"sl":45}],"name":"testServiceInjection","statements":[{"sl":32},{"sl":37},{"sl":47},{"sl":48}],"pass":true},"test_20":{"methods":[{"sl":31},{"sl":35}],"name":"testDecoratedServiceInjection","statements":[{"sl":32},{"sl":37}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [20, 21, 1], [20, 21, 1], [], [], [20, 21, 12, 1], [], [20, 21, 12, 1], [], [], [], [], [], [], [], [21], [], [21], [21], [], [], [], []]
