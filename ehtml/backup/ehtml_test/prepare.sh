#!/bin/bash

# css等を更新した場合（クエリを変更した場合も？）はキャッシュを削除
rm -rf Ssql/tmp/*

rm -rf Ssql/sqlResults/index/*
rm -rf Ssql/sqlResults/r_details/*
rm -rf Ssql/sqlResults/ssql/*

rm -rf GeneratedXML/index/*
rm -rf GeneratedXML/r_details/*
rm -rf GeneratedXML/ssql/*

rm -rf jscss/index/*
rm -rf jscss/r_details/*
rm -rf jscss/ssql/*
