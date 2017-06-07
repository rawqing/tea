<header class="header black-bg">
    <div>
        <div class="sidebar-toggle-box">
            <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
        </div>
        <!--logo start-->
        <a href="/" class="logo"><b>${Evn.p_name}</b></a>
    </div>
    <!--logo end-->
    <div class="nav notify-row" id="top_menu">
    </div>
    <div>
        <ul class="top-menu nav_bar">
            <g:each in="${Evn.navigation}" var="bar">
                <li><a href="${bar.uri}">${bar.name}</a> </li>
            </g:each>
        </ul>
    </div>
    <div class="top-menu">
        <ul class="nav pull-right top-menu">
            <li><a class="logout" href="">Logout</a></li>
        </ul>
    </div>
</header>
