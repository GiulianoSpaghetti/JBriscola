Name:               jbriscola
Version:            0.3.1
Release:            1%{?dist}
License:            GPLv3+
Group:              Amusements/Games/CardGames
Source:             %{name}-%{version}-src.tar.bz2
URL:                http://github.com/numerunix/jbriscola
BuildRequires:      meson, ninja-build, java-latest-openjdk-devel
Requires:     	    java-latest-openjdk
Summary:            Il simulatore del gioco della briscola per due giocatori
BuildArch:	    noarch
%description
Il simulatore del gioco della briscola per 2 giocatori.
Manca il multiplayer...

%prep
%autosetup -n %{name}

%build
%meson
%meson_build

%install
%meson_install

%files
/opt/jbriscola/jbriscola-0.3.1.jar
%{_datadir}/applications/JBriscola.desktop
%{_datadir}/icons/JBriscola.ico


%changelog
* Fri Nov 27 2020 Giulio Sorrentino <gsorre84@gmail.com> 0.3.1-1
- Risolto il bug di visualizzazione dei mazzi dr francy e gatti
- Risolto il bug che non aggiornava le metriche del font al cambio dello stesso

* Fri Nov 27 2020 Giulio Sorrentino <gsorre84@gmail.com> 0.3-1
- Initial release (#00000)
