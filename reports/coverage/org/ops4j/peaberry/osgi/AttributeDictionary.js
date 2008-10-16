var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":842,"sl":31,"methods":[{"sl":36,"el":38,"sc":3},{"sl":40,"el":43,"sc":3},{"sl":45,"el":48,"sc":3},{"sl":50,"el":53,"sc":3},{"sl":55,"el":58,"sc":3},{"sl":60,"el":64,"sc":3},{"sl":66,"el":70,"sc":3},{"sl":72,"el":76,"sc":3}],"el":77,"name":"AttributeDictionary"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_2":{"methods":[{"sl":36},{"sl":50},{"sl":55},{"sl":60},{"sl":66},{"sl":72}],"name":"testAttributeDictionaryAdapter","statements":[{"sl":37},{"sl":52},{"sl":57},{"sl":63},{"sl":69},{"sl":75}],"pass":true},"test_10":{"methods":[{"sl":36},{"sl":40},{"sl":45}],"name":"testServiceExports","statements":[{"sl":37},{"sl":42},{"sl":47}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [2, 10], [2, 10], [], [], [10], [], [10], [], [], [10], [], [10], [], [], [2], [], [2], [], [], [2], [], [2], [], [], [2], [], [], [2], [], [], [2], [], [], [2], [], [], [2], [], [], [2], [], []]
