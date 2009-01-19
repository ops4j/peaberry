var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":213,"sl":35,"methods":[{"sl":46,"el":52,"sc":3},{"sl":54,"el":98,"sc":3},{"sl":61,"el":91,"sc":7},{"sl":93,"el":96,"sc":7},{"sl":100,"el":103,"sc":3}],"el":104,"name":"BundleScope"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_11":{"methods":[{"sl":61}],"name":"testServiceLookupPerformance","statements":[{"sl":64},{"sl":90}],"pass":true},"test_9":{"methods":[{"sl":61}],"name":"testDecoratedServiceInjection","statements":[{"sl":64},{"sl":90}],"pass":true},"test_5":{"methods":[{"sl":61}],"name":"testDirectServiceInjection","statements":[{"sl":64},{"sl":90}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [5, 9, 11], [], [], [5, 9, 11], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [5, 9, 11], [], [], [], [], [], [], [], [], [], [], [], [], [], []]
