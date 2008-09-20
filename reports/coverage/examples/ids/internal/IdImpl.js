var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":908,"sl":26,"methods":[{"sl":31,"el":33,"sc":3},{"sl":35,"el":38,"sc":3},{"sl":40,"el":43,"sc":3},{"sl":45,"el":51,"sc":3}],"el":52,"name":"IdImpl"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_8":{"methods":[{"sl":31},{"sl":35},{"sl":45}],"name":"testServiceInjection","statements":[{"sl":32},{"sl":37},{"sl":47},{"sl":48}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [8], [8], [], [], [8], [], [8], [], [], [], [], [], [], [], [8], [], [8], [8], [], [], [], []]
