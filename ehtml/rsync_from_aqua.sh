#!/bin/bash
rsync -av aqua.db.ics.keio.ac.jp:/srv/http/htdocs/ssql/goto/essql/aii/ backup/aii/
rsync -av aqua.db.ics.keio.ac.jp:/srv/http/htdocs/ssql/goto/essql/aii_dev/ backup/aii_dev/
#rsync -av aqua.db.ics.keio.ac.jp:/srv/http/htdocs/ssql/goto/essql/ehtml_test/ backup/ehtml_test/
