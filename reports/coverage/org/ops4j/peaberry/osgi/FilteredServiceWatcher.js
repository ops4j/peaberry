var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":2271,"sl":32,"methods":[{"sl":38,"el":41,"sc":3},{"sl":43,"el":46,"sc":3},{"sl":96,"el":103,"sc":3},{"sl":105,"el":108,"sc":3}],"el":109,"name":"FilteredServiceWatcher"},{"id":2276,"sl":48,"methods":[{"sl":53,"el":57,"sc":5},{"sl":59,"el":71,"sc":5},{"sl":75,"el":82,"sc":5},{"sl":84,"el":93,"sc":5}],"el":94,"name":"FilteredServiceWatcher.FilteredExport"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":43},{"sl":53},{"sl":59}],"name":"testServiceInterception","statements":[{"sl":45},{"sl":54},{"sl":56},{"sl":60},{"sl":61},{"sl":63}],"pass":true},"test_3":{"methods":[{"sl":43},{"sl":53},{"sl":59},{"sl":75}],"name":"testServiceInjection","statements":[{"sl":45},{"sl":54},{"sl":56},{"sl":60},{"sl":61},{"sl":63},{"sl":77},{"sl":79},{"sl":80}],"pass":true},"test_74":{"methods":[{"sl":43},{"sl":53},{"sl":59}],"name":"testDecoratedServiceInjection","statements":[{"sl":45},{"sl":54},{"sl":56},{"sl":60},{"sl":61},{"sl":63}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [1, 3, 74], [], [1, 3, 74], [], [], [], [], [], [], [], [1, 3, 74], [1, 3, 74], [], [1, 3, 74], [], [], [1, 3, 74], [1, 3, 74], [1, 3, 74], [], [1, 3, 74], [], [], [], [], [], [], [], [], [], [], [], [3], [], [3], [], [3], [3], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
