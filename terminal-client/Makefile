.venv/touchfile: requirements.txt
	# create virtualenv if not exists
	test -d .venv || python -m venv .venv
	. .venv/bin/activate; pip install -r requirements.txt
	touch .venv/touchfile

# touching .venv/touchfile and using it as a prerequisite means we will only re-install 
# if requirements.txt changes.
install: .venv/touchfile

run: install
	. .venv/bin/activate; python app.py

.PHONY: install run
