#!/bin/sh

REPORTS="reports extensions/eclipse/reports"

if stat -t ${REPORTS} 1>/dev/null 2>/dev/null
then
  zip -rq OLD_REPORTS.zip ${REPORTS}
  svn del --force ${REPORTS}
fi

echo ${REPORTS//reports/build.xml} | xargs -n1 ant wipe reports -f

ant wipe dist test.dist

find ${REPORTS} -type f -exec dos2unix {} \;

svn add ${REPORTS}

for f in `find ${REPORTS} -type f | grep -v .svn`
do
  case $f in
    *.js)   svn propset svn:mime-type "text/javascript" $f ;;
    *.html) svn propset svn:mime-type "text/html" $f ;;
    *.css)  svn propset svn:mime-type "text/css" $f ;;
  esac
done

