var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1547,"sl":42,"methods":[{"sl":51,"el":73,"sc":3},{"sl":55,"el":60,"sc":7},{"sl":62,"el":65,"sc":7},{"sl":67,"el":70,"sc":7},{"sl":75,"el":117,"sc":3}],"el":118,"name":"ServiceInjectionTests"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_28":{"methods":[{"sl":55},{"sl":67},{"sl":75}],"name":"testServiceInjection","statements":[{"sl":57},{"sl":58},{"sl":59},{"sl":69},{"sl":76},{"sl":78},{"sl":80},{"sl":81},{"sl":82},{"sl":84},{"sl":86},{"sl":87},{"sl":88},{"sl":90},{"sl":92},{"sl":93},{"sl":94},{"sl":95},{"sl":97},{"sl":99},{"sl":100},{"sl":101},{"sl":103},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":110},{"sl":111},{"sl":115},{"sl":116}],"pass":true},"test_9":{"methods":[{"sl":55}],"name":"testDecoratedServiceInjection","statements":[{"sl":57},{"sl":58},{"sl":59}],"pass":true},"test_5":{"methods":[{"sl":55},{"sl":67}],"name":"testDirectServiceInjection","statements":[{"sl":57},{"sl":58},{"sl":59},{"sl":69}],"pass":true},"test_18":{"methods":[{"sl":51}],"name":"configure","statements":[{"sl":53},{"sl":72}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [18], [], [18], [], [5, 28, 9], [], [5, 28, 9], [5, 28, 9], [5, 28, 9], [], [], [], [], [], [], [], [5, 28], [], [5, 28], [], [], [18], [], [], [28], [28], [], [28], [], [28], [28], [28], [], [28], [], [28], [28], [28], [], [28], [], [28], [28], [28], [28], [], [28], [], [28], [28], [28], [], [28], [], [28], [28], [28], [28], [], [28], [28], [], [], [], [28], [28], [], []]
