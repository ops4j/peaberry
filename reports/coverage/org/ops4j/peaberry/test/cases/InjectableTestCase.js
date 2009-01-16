var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1736,"sl":43,"methods":[{"sl":52,"el":62,"sc":3},{"sl":55,"el":58,"sc":11},{"sl":64,"el":66,"sc":3},{"sl":68,"el":71,"sc":3},{"sl":73,"el":75,"sc":3},{"sl":77,"el":82,"sc":3},{"sl":84,"el":86,"sc":3},{"sl":88,"el":90,"sc":3},{"sl":92,"el":94,"sc":3}],"el":95,"name":"InjectableTestCase"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_13":{"methods":[{"sl":64},{"sl":68},{"sl":84},{"sl":92}],"name":"testDecoratedServiceInjection","statements":[{"sl":65},{"sl":70},{"sl":85},{"sl":93}],"pass":true},"test_11":{"methods":[{"sl":64},{"sl":84},{"sl":92}],"name":"testDirectServiceInjection","statements":[{"sl":65},{"sl":85},{"sl":93}],"pass":true},"test_22":{"methods":[{"sl":64},{"sl":68},{"sl":77},{"sl":84},{"sl":88},{"sl":92}],"name":"testServiceExports","statements":[{"sl":65},{"sl":70},{"sl":78},{"sl":79},{"sl":85},{"sl":89},{"sl":93}],"pass":true},"test_5":{"methods":[{"sl":73},{"sl":77},{"sl":84},{"sl":88},{"sl":92}],"name":"testServiceInjection","statements":[{"sl":74},{"sl":78},{"sl":79},{"sl":85},{"sl":89},{"sl":93}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [11, 22, 13], [11, 22, 13], [], [], [22, 13], [], [22, 13], [], [], [5], [5], [], [], [22, 5], [22, 5], [22, 5], [], [], [], [], [11, 22, 13, 5], [11, 22, 13, 5], [], [], [22, 5], [22, 5], [], [], [11, 22, 13, 5], [11, 22, 13, 5], [], []]
