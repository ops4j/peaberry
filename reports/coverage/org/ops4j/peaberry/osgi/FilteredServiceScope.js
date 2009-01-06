var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":101,"sl":29,"methods":[{"sl":35,"el":38,"sc":3},{"sl":40,"el":45,"sc":3},{"sl":47,"el":54,"sc":3},{"sl":56,"el":59,"sc":3},{"sl":61,"el":63,"sc":3}],"el":64,"name":"FilteredServiceScope"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":40}],"name":"testServiceExports","statements":[{"sl":41},{"sl":42}],"pass":true},"test_12":{"methods":[{"sl":40}],"name":"testDirectServiceInjection","statements":[{"sl":41},{"sl":42}],"pass":true},"test_21":{"methods":[{"sl":40}],"name":"testServiceInjection","statements":[{"sl":41},{"sl":42}],"pass":true},"test_20":{"methods":[{"sl":40}],"name":"testDecoratedServiceInjection","statements":[{"sl":41},{"sl":42}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [20, 21, 12, 1], [20, 21, 12, 1], [20, 21, 12, 1], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
