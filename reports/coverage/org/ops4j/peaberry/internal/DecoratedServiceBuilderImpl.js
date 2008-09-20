var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":444,"sl":43,"methods":[{"sl":57,"el":59,"sc":3},{"sl":61,"el":64,"sc":3},{"sl":66,"el":69,"sc":3},{"sl":71,"el":74,"sc":3},{"sl":76,"el":79,"sc":3},{"sl":81,"el":100,"sc":3},{"sl":87,"el":98,"sc":7},{"sl":102,"el":121,"sc":3},{"sl":108,"el":119,"sc":7},{"sl":123,"el":125,"sc":3},{"sl":127,"el":133,"sc":3}],"el":134,"name":"DecoratedServiceBuilderImpl"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_0":{"methods":[{"sl":57},{"sl":66},{"sl":81}],"name":"configure","statements":[{"sl":58},{"sl":67},{"sl":68},{"sl":82}],"pass":true},"test_10":{"methods":[{"sl":57},{"sl":81}],"name":"configure","statements":[{"sl":58},{"sl":82}],"pass":true},"test_14":{"methods":[{"sl":57},{"sl":81},{"sl":102}],"name":"configure","statements":[{"sl":58},{"sl":82},{"sl":103}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [10, 0, 14], [10, 0, 14], [], [], [], [], [], [], [], [0], [0], [0], [], [], [], [], [], [], [], [], [], [], [], [], [10, 0, 14], [10, 0, 14], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [14], [14], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
