var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1196,"sl":35,"methods":[{"sl":38,"el":38,"sc":3},{"sl":52,"el":54,"sc":3},{"sl":62,"el":65,"sc":3},{"sl":75,"el":78,"sc":3}],"el":79,"name":"Decorators"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_73":{"methods":[{"sl":75}],"name":"testMissingInterceptor","statements":[{"sl":77}],"pass":true},"test_5":{"methods":[{"sl":52}],"name":"configure","statements":[{"sl":53}],"pass":true},"test_18":{"methods":[{"sl":62},{"sl":75}],"name":"configure","statements":[{"sl":64},{"sl":77}],"pass":true},"test_44":{"methods":[{"sl":62}],"name":"testDecoratorChaining","statements":[{"sl":64}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [5], [5], [], [], [], [], [], [], [], [], [44, 18], [], [44, 18], [], [], [], [], [], [], [], [], [], [], [73, 18], [], [73, 18], [], []]
