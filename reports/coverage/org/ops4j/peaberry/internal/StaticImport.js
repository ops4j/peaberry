var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":959,"sl":28,"methods":[{"sl":34,"el":37,"sc":3},{"sl":39,"el":41,"sc":3},{"sl":43,"el":45,"sc":3},{"sl":47,"el":47,"sc":3}],"el":48,"name":"StaticImport"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_28":{"methods":[{"sl":34},{"sl":39},{"sl":43},{"sl":47}],"name":"testServiceInjection","statements":[{"sl":35},{"sl":36},{"sl":40},{"sl":44}],"pass":true},"test_12":{"methods":[{"sl":34},{"sl":39},{"sl":47}],"name":"testProxyPerformance","statements":[{"sl":35},{"sl":36},{"sl":40}],"pass":true},"test_11":{"methods":[{"sl":34},{"sl":39},{"sl":43}],"name":"testServiceLookupPerformance","statements":[{"sl":35},{"sl":36},{"sl":40},{"sl":44}],"pass":true},"test_9":{"methods":[{"sl":34},{"sl":39},{"sl":43}],"name":"testDecoratedServiceInjection","statements":[{"sl":35},{"sl":36},{"sl":40},{"sl":44}],"pass":true},"test_5":{"methods":[{"sl":34},{"sl":39},{"sl":43},{"sl":47}],"name":"testDirectServiceInjection","statements":[{"sl":35},{"sl":36},{"sl":40},{"sl":44}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [5, 28, 9, 11, 12], [5, 28, 9, 11, 12], [5, 28, 9, 11, 12], [], [], [5, 28, 9, 11, 12], [5, 28, 9, 11, 12], [], [], [5, 28, 9, 11], [5, 28, 9, 11], [], [], [5, 28, 12], []]
