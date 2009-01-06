var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1730,"sl":43,"methods":[{"sl":52,"el":62,"sc":3},{"sl":55,"el":58,"sc":11},{"sl":64,"el":66,"sc":3},{"sl":68,"el":71,"sc":3},{"sl":73,"el":75,"sc":3},{"sl":77,"el":82,"sc":3},{"sl":84,"el":86,"sc":3},{"sl":88,"el":90,"sc":3},{"sl":92,"el":94,"sc":3}],"el":95,"name":"InjectableTestCase"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":64},{"sl":68},{"sl":77},{"sl":84},{"sl":88},{"sl":92}],"name":"testServiceExports","statements":[{"sl":65},{"sl":70},{"sl":78},{"sl":79},{"sl":85},{"sl":89},{"sl":93}],"pass":true},"test_12":{"methods":[{"sl":64},{"sl":84},{"sl":92}],"name":"testDirectServiceInjection","statements":[{"sl":65},{"sl":85},{"sl":93}],"pass":true},"test_21":{"methods":[{"sl":73},{"sl":77},{"sl":84},{"sl":88},{"sl":92}],"name":"testServiceInjection","statements":[{"sl":74},{"sl":78},{"sl":79},{"sl":85},{"sl":89},{"sl":93}],"pass":true},"test_20":{"methods":[{"sl":64},{"sl":68},{"sl":84},{"sl":92}],"name":"testDecoratedServiceInjection","statements":[{"sl":65},{"sl":70},{"sl":85},{"sl":93}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [20, 12, 1], [20, 12, 1], [], [], [20, 1], [], [20, 1], [], [], [21], [21], [], [], [21, 1], [21, 1], [21, 1], [], [], [], [], [20, 21, 12, 1], [20, 21, 12, 1], [], [], [21, 1], [21, 1], [], [], [20, 21, 12, 1], [20, 21, 12, 1], [], []]
