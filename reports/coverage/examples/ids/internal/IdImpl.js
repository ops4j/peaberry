var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1276,"sl":26,"methods":[{"sl":31,"el":33,"sc":3},{"sl":35,"el":38,"sc":3},{"sl":40,"el":43,"sc":3},{"sl":45,"el":51,"sc":3}],"el":52,"name":"IdImpl"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_11":{"methods":[{"sl":31},{"sl":35}],"name":"testServiceExports","statements":[{"sl":32},{"sl":37}],"pass":true},"test_16":{"methods":[{"sl":31},{"sl":35},{"sl":45}],"name":"testServiceInjection","statements":[{"sl":32},{"sl":37},{"sl":47},{"sl":48}],"pass":true},"test_14":{"methods":[{"sl":35}],"name":"testDirectServiceInjection","statements":[{"sl":37}],"pass":true},"test_6":{"methods":[{"sl":31},{"sl":35}],"name":"testDecoratedServiceInjection","statements":[{"sl":32},{"sl":37}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [16, 11, 6], [16, 11, 6], [], [], [16, 14, 11, 6], [], [16, 14, 11, 6], [], [], [], [], [], [], [], [16], [], [16], [16], [], [], [], []]
