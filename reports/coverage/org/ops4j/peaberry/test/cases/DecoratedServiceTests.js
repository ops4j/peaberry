var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1110,"sl":38,"methods":[{"sl":77,"el":84,"sc":3},{"sl":86,"el":95,"sc":3}],"el":96,"name":"DecoratedServiceTests"},{"id":1110,"sl":41,"methods":[{"sl":46,"el":48,"sc":5},{"sl":50,"el":53,"sc":5}],"el":54,"name":"DecoratedServiceTests.IdAdapter"},{"id":1114,"sl":56,"methods":[{"sl":59,"el":71,"sc":5},{"sl":62,"el":65,"sc":9},{"sl":67,"el":69,"sc":9}],"el":72,"name":"DecoratedServiceTests.IdDecorator"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_10":{"methods":[{"sl":77}],"name":"configure","statements":[{"sl":79},{"sl":82},{"sl":83}],"pass":true},"test_25":{"methods":[{"sl":46},{"sl":50},{"sl":59},{"sl":62},{"sl":67},{"sl":86}],"name":"testDecoratedServiceInjection","statements":[{"sl":47},{"sl":52},{"sl":60},{"sl":64},{"sl":68},{"sl":87},{"sl":89},{"sl":92},{"sl":94}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [25], [25], [], [], [25], [], [25], [], [], [], [], [], [], [25], [25], [], [25], [], [25], [], [], [25], [25], [], [], [], [], [], [], [], [], [10], [], [10], [], [], [10], [10], [], [], [25], [25], [], [25], [], [], [25], [], [25], [], []]
