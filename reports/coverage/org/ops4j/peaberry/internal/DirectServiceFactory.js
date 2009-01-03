var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":172,"sl":34,"methods":[{"sl":37,"el":37,"sc":3},{"sl":39,"el":54,"sc":3},{"sl":56,"el":69,"sc":3},{"sl":71,"el":79,"sc":3}],"el":80,"name":"DirectServiceFactory"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_4":{"methods":[{"sl":56},{"sl":71}],"name":"testServiceLookupPerformance","statements":[{"sl":59},{"sl":61},{"sl":63},{"sl":64},{"sl":65},{"sl":74},{"sl":75}],"pass":true},"test_14":{"methods":[{"sl":39},{"sl":56},{"sl":71}],"name":"testDirectServiceInjection","statements":[{"sl":42},{"sl":43},{"sl":45},{"sl":47},{"sl":48},{"sl":49},{"sl":53},{"sl":59},{"sl":61},{"sl":63},{"sl":64},{"sl":65},{"sl":68},{"sl":74},{"sl":75}],"pass":true},"test_6":{"methods":[{"sl":56},{"sl":71}],"name":"testDecoratedServiceInjection","statements":[{"sl":59},{"sl":61},{"sl":63},{"sl":64},{"sl":65},{"sl":74},{"sl":75}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [14], [], [], [14], [14], [], [14], [], [14], [14], [14], [], [], [], [14], [], [], [4, 14, 6], [], [], [4, 14, 6], [], [4, 14, 6], [], [4, 14, 6], [4, 14, 6], [4, 14, 6], [], [], [14], [], [], [4, 14, 6], [], [], [4, 14, 6], [4, 14, 6], [], [], [], [], []]
