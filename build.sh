[ -d "build" ] && rm -r build
cd shadowstrike
ant -Dnb.internal.action.name=build jar
cp -r dist ../build
