var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":5,"sl":35,"methods":[{"sl":47,"el":50,"sc":3},{"sl":52,"el":55,"sc":3},{"sl":57,"el":60,"sc":3},{"sl":62,"el":64,"sc":3}],"el":65,"name":"QualifiedRegistrationBuilderImpl"},{"id":5,"sl":42,"methods":[],"el":45,"name":"QualifiedRegistrationBuilderImpl.Configuration"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_3":{"methods":[{"sl":47},{"sl":62}],"name":"configure","statements":[{"sl":48},{"sl":49},{"sl":63}],"pass":true},"test_4":{"methods":[{"sl":47},{"sl":57},{"sl":62}],"name":"configure","statements":[{"sl":48},{"sl":49},{"sl":58},{"sl":59},{"sl":63}],"pass":true},"test_26":{"methods":[{"sl":47},{"sl":52},{"sl":62}],"name":"configure","statements":[{"sl":48},{"sl":49},{"sl":53},{"sl":54},{"sl":63}],"pass":true},"test_17":{"methods":[{"sl":47},{"sl":62}],"name":"configure","statements":[{"sl":48},{"sl":49},{"sl":63}],"pass":true},"test_19":{"methods":[{"sl":47},{"sl":62}],"name":"configure","statements":[{"sl":48},{"sl":49},{"sl":63}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [3, 17, 26, 4, 19], [3, 17, 26, 4, 19], [3, 17, 26, 4, 19], [], [], [26], [26], [26], [], [], [4], [4], [4], [], [], [3, 17, 26, 4, 19], [3, 17, 26, 4, 19], [], []]
