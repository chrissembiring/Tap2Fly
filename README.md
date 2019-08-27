# Tap2Fly

Tap2Fly is a proposed flight check-in and boarding system utilising Android OS, SQLite and Near Field Communication (NFC) technology. Tap2Fly was one of the projects I completed as a graduation requirement in Nanyang Polytechnic. This Android application was submitted in late 2015.

## Compatibility
As this app was written in 2015, it was designed specifically for Android 5.0 Lollipop (API level 21). This app uses NFC Data Exchange Format (NDEF) to exchange information between the phone and NFC tags. NDEF was supported by Android since the release of Android 4.0.1 Ice Cream Sandwich (API level 14).

The app also records the flight and passenger details into the embedded phone's SQLite database. However, the methods used in this app may be deprecated when Android Q (API level 29) is released. The app at its current state is still usable, however since this app is a largely unmaintained project, this may no longer be the case in the near future.

## Purpose
Before this app was made, I was largely interested in air transport. I thought the current way of checking in passengers can be streamlined in several ways, as follows:

-__Privacy__
Boarding passes are printed with sensitive data, readable by anyone else who comes across them. I proposed that such boarding passes be replaced with an NFC tag, so that data including flight and seat numbers are hidden, embedded inside an inconspicuous plastic NFC tag.

-__Paper Wastage__
Boarding passes loses their purpose right after passengers board their aircraft. These boarding passes are then thrown away, hence wasting paper. NFC tags, on the other hand, can be used multiple times. Some tags such as NXP NTAG213 can be rewritten up to 100,000 times. Passengers boarding their aircraft would hand their NFC boarding pass tags to the last airport staff to be scanned and handed over for next usage.

Please notify me or raise issue should there be any errors or bugs found when running these scripts.
