var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1700,"sl":40,"methods":[{"sl":83,"el":90,"sc":3},{"sl":92,"el":101,"sc":3}],"el":102,"name":"DecoratedServiceTests"},{"id":1700,"sl":43,"methods":[{"sl":48,"el":50,"sc":5},{"sl":52,"el":55,"sc":5}],"el":56,"name":"DecoratedServiceTests.IdAdapter"},{"id":1704,"sl":58,"methods":[{"sl":61,"el":77,"sc":5},{"sl":64,"el":67,"sc":9},{"sl":69,"el":71,"sc":9},{"sl":73,"el":75,"sc":9}],"el":78,"name":"DecoratedServiceTests.IdDecorator"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_32":{"methods":[{"sl":48},{"sl":52},{"sl":61},{"sl":64},{"sl":73},{"sl":92}],"name":"testDecoratedServiceInjection","statements":[{"sl":49},{"sl":54},{"sl":62},{"sl":66},{"sl":74},{"sl":93},{"sl":95},{"sl":98},{"sl":100}],"pass":true},"test_30":{"methods":[{"sl":83}],"name":"configure","statements":[{"sl":85},{"sl":88},{"sl":89}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [32], [32], [], [], [32], [], [32], [], [], [], [], [], [], [32], [32], [], [32], [], [32], [], [], [], [], [], [], [32], [32], [], [], [], [], [], [], [], [], [30], [], [30], [], [], [30], [30], [], [], [32], [32], [], [32], [], [], [32], [], [32], [], []]
