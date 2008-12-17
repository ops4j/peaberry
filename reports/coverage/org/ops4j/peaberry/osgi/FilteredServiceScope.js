var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":35,"sl":29,"methods":[{"sl":35,"el":38,"sc":3},{"sl":40,"el":45,"sc":3},{"sl":47,"el":54,"sc":3},{"sl":56,"el":59,"sc":3},{"sl":61,"el":63,"sc":3}],"el":64,"name":"FilteredServiceScope"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_25":{"methods":[{"sl":40}],"name":"testServiceExports","statements":[{"sl":41},{"sl":42}],"pass":true},"test_32":{"methods":[{"sl":40}],"name":"testDecoratedServiceInjection","statements":[{"sl":41},{"sl":42}],"pass":true},"test_15":{"methods":[{"sl":40}],"name":"testDirectServiceInjection","statements":[{"sl":41},{"sl":42}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [32, 15, 25], [32, 15, 25], [32, 15, 25], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
