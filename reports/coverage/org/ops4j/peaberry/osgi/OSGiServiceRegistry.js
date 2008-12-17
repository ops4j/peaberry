var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":153,"sl":38,"methods":[{"sl":46,"el":54,"sc":3},{"sl":56,"el":58,"sc":3},{"sl":60,"el":62,"sc":3},{"sl":64,"el":69,"sc":3},{"sl":71,"el":76,"sc":3},{"sl":78,"el":81,"sc":3},{"sl":83,"el":98,"sc":3}],"el":99,"name":"OSGiServiceRegistry"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_25":{"methods":[{"sl":60}],"name":"testServiceExports","statements":[{"sl":61}],"pass":true},"test_10":{"methods":[{"sl":56},{"sl":60},{"sl":83}],"name":"testServiceLookupPerformance","statements":[{"sl":57},{"sl":61},{"sl":84},{"sl":85},{"sl":87},{"sl":88},{"sl":89},{"sl":90},{"sl":91},{"sl":92},{"sl":93},{"sl":97}],"pass":true},"test_32":{"methods":[{"sl":56},{"sl":83}],"name":"testDecoratedServiceInjection","statements":[{"sl":57},{"sl":84},{"sl":85},{"sl":87},{"sl":88},{"sl":97}],"pass":true},"test_15":{"methods":[{"sl":56},{"sl":60},{"sl":83}],"name":"testDirectServiceInjection","statements":[{"sl":57},{"sl":61},{"sl":84},{"sl":85},{"sl":87},{"sl":88},{"sl":89},{"sl":90},{"sl":91},{"sl":92},{"sl":93},{"sl":97}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [32, 15, 10], [32, 15, 10], [], [], [15, 10, 25], [15, 10, 25], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [32, 15, 10], [32, 15, 10], [32, 15, 10], [], [32, 15, 10], [32, 15, 10], [15, 10], [15, 10], [15, 10], [15, 10], [15, 10], [], [], [], [32, 15, 10], [], []]
